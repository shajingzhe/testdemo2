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

	private Integer salary;

	private String sex;

	private String area;

	public Person(String name, Integer age, Integer salary, String sex, String area) {
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.sex = sex;
		this.area = area;
	}

	public Person(String name, Integer age, Integer salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
}
