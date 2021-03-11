package com.epam.cloud.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;

public class ConvertHelper {

    public static <SOURCE, TARGET> List<TARGET> convertAll(Collection<? extends SOURCE> sourceList,
                                                           Converter<SOURCE, TARGET> converter) {

        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        return sourceList.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
