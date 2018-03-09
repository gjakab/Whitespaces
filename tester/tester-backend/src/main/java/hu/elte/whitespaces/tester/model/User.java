package hu.elte.whitespaces.tester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String lastname;
	
	@Column(nullable = false)
	private String school;
		
	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

}
