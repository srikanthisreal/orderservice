package com.example.orderservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Order Services.
 * 
 * @author Srikanth
 *
 */
@Slf4j
@Service
public class OrderService {

	@Autowired
	private Environment env;

	@Autowired
	ResourceLoader resourceLoader;

	public LocalDate getExpectedDate(LocalDateTime date, String provider) {
		int deliveryTime = Integer.valueOf(env.getProperty("provider." + provider));
		int cutOffTime = Integer.valueOf(env.getProperty("order.cutoff.time"));

		LocalDate deliveryDate = null;
		Resource resource = resourceLoader.getResource("classpath:feiertage-in-deutschland-2-data.properties");
		try (InputStream inputStream = resource.getInputStream()) {
			Properties prop = new Properties();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// load a properties file
			prop.load(inputStream);
			Set<Object> holiday = prop.keySet();
			// prepare list of Holidays
			Set<LocalDate> hashSet = new HashSet<LocalDate>();
			holiday.forEach(obj -> hashSet.add(LocalDate.parse((String) obj, formatter)));
			// get delivery date, if date is null then default current date taken
			if(date == null)date = LocalDateTime.now();
			deliveryDate = expectedDeliveryDate(
					(date.getHour() >= cutOffTime) ? date.plusDays(1l).toLocalDate() : date.toLocalDate(), deliveryTime,
					Optional.of(hashSet));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		log.info("actual datetime {}, expected delivery date {}", date, deliveryDate);
		return deliveryDate;
	}

	private static LocalDate expectedDeliveryDate(LocalDate localDate, int days, Optional<Set<LocalDate>> holidays) {
		if (localDate == null || days <= 0 || holidays == null) {
			throw new IllegalArgumentException("Invalid method argument(s) " + "to expectedDate(" + localDate + ","
					+ days + "," + holidays + ")");
		}
		Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;
		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
		LocalDate result = localDate;
		while (days > 0) {
			result = result.plusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
			}
		}
		return result;
	}

}
