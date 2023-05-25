package com.fincons.itsle.rest.clientjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private String jwtToken;

	private long expireIn;

	@JsonIgnore
	public synchronized Boolean isNotExpired() {
		return System.currentTimeMillis() < expireIn;
	}
}