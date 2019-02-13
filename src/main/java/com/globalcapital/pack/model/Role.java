package com.globalcapital.pack.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor*/

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "role_name")
	private String role_name;
}
