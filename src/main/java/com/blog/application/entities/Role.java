package com.blog.application.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


	@Entity
	@Table(name = "roles")
	public class Role {

		@Id
		private Integer id;
		
		@NotEmpty
		private String name;

		public Role() {}
		
		public Role(Integer id, @NotEmpty String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public long getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Role [id=" + id + ", name=" + name + "]";
		}
}