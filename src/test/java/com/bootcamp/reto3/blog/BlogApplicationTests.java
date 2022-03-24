package com.bootcamp.reto3.blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@Slf4j
@SpringBootTest
class BlogApplicationTests {

	@Test
	void contextLoads() {
		Map<String, Integer> mapInt= new HashMap<>();
		mapInt.put("hola", 123);
		mapInt.put("hola2", 1234);
		mapInt.put("hola3", 1235);

		mapInt.size();
		log.info("tamaño {}", mapInt.size());

		List<String> list = new ArrayList<>(Arrays.asList("1","2"));
		List<String> list2 = new ArrayList<>();

	}

}
