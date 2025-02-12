package com.example.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LambdaTest {

	@Test
	public void sort() {
		List<SampleVO> list = Arrays.asList(
				new SampleVO("홍길동",10),
				new SampleVO("김유신",20),
				new SampleVO("최기자",15));
		//Collections.sort(list, new SampleCompare());
		//익명클래스
		Collections.sort(list,  (SampleVO o1, SampleVO o2) ->{
				return o2.getName().compareTo(o1.getName());
			}   );
		System.out.println(list);
	}
}

class SampleCompare implements Comparator<SampleVO> {

	@Override
	public int compare(SampleVO o1, SampleVO o2) {
		return o2.getName().compareTo(o1.getName());
	}
	
}



