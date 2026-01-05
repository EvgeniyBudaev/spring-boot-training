package com.aggregationjpa.service;

import com.aggregationjpa.controller.dto.request.RequestItemCreateDto;
import com.aggregationjpa.controller.dto.request.RequestItemUpdateDto;
import com.aggregationjpa.controller.dto.response.PagedResponseItemDto;
import com.aggregationjpa.controller.dto.response.Paging;
import com.aggregationjpa.controller.dto.response.ResponseItemDto;
import com.aggregationjpa.entity.ItemEntity;
import com.aggregationjpa.repository.ItemRepository;
import com.aggregationjpa.service.mapper.ItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public void saveItem(RequestItemCreateDto request) {
        ItemEntity itemEntity = itemMapper.toEntity(request);
        itemRepository.save(itemEntity);
    }

    @Override
    @Transactional
    public ResponseItemDto updateItem(Long id, RequestItemUpdateDto request) {
        ItemEntity existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item with id=" + id + " not found"));

        // Обновляем поля через MapStruct
        itemMapper.updateEntityFromDto(request, existingItem);

        // Сохраняем (Hibernate обновит запись, так как сущность managed)
        ItemEntity updatedItem = itemRepository.save(existingItem);

        return itemMapper.toResponseItemDto(updatedItem);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item with id=" + id + " not found");
        }

        itemRepository.deleteById(id);
    }

    @Override
    public ResponseItemDto getItem(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item with id=" + id + " not found"));

        return itemMapper.toResponseItemDto(itemEntity);
    }

    @Override
    public PagedResponseItemDto getItems(String search, String sortBy, String sortOrder, int pageNumber, int pageSize) {
        // Разрешённые поля для сортировки — защита от SQL-инъекций через имена колонок
        Set<String> allowedFields = Set.of("title", "price");

        Sort sort = Sort.unsorted();
        if (sortBy != null && allowedFields.contains(sortBy.toLowerCase())) {
            Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder)
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sort = Sort.by(direction, sortBy);
        }

        // Spring Data использует 0-based номера страниц
        var pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<ItemEntity> itemsPage;
        if (StringUtils.hasText(search)) {
            itemsPage = itemRepository
                    .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
        } else {
            itemsPage = itemRepository.findAll(pageable);
        }

        List<ResponseItemDto> content = itemsPage.getContent().stream()
                .map(itemMapper::toResponseItemDto)
                .toList();

        Paging paging = new Paging(
                pageSize,
                pageNumber,
                itemsPage.hasPrevious(),
                itemsPage.hasNext()
        );

        return new PagedResponseItemDto(content, search, sortBy, sortOrder, paging);
    }
}
