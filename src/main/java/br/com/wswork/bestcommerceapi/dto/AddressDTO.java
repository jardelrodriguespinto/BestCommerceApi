package br.com.wswork.bestcommerceapi.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.wswork.bestcommerceapi.model.Address;
import lombok.*;
@Builder
@Getter
public class AddressDTO {

	Long id;
	int number;
	String neighbourhood;
	String state;
	String city;
	String country;
	
	public static AddressDTO convertEntityToDTO(Address address) {
		
		return AddressDTO.builder()
						 .id(address.getId())
						 .number(address.getNumber())
						 .neighbourhood(address.getNeighbourhood())
						 .state(address.getState())
						 .city(address.getCity())
						 .country(address.getCountry())
						 .build();
	}
	
	public static List<AddressDTO> convertListEntityToDTO(List<Address> addresses) {

		return addresses.stream()
						.map(AddressDTO::convertEntityToDTO)
						.collect(Collectors.toList());
	}
}