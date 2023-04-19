package entity;

import lombok.Data;

/**
 * @author shajingzhe
 * @date 2023/4/12 下午5:12
 */

@Data
public class Person {

	private String name;

	private Integer age;

	private Integer Salary;

	private String Sex;

	private String area;

	public Person(String name, Integer age, Integer salary, String sex, String area) {
		this.name = name;
		this.age = age;
		Salary = salary;
		Sex = sex;
		this.area = area;
	}
}
