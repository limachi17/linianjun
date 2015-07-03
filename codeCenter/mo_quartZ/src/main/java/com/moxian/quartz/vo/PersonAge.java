package com.moxian.quartz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonAge {
	private int age;
	private String name;

}
