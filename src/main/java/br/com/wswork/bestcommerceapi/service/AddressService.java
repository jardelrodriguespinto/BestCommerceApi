package br.com.wswork.bestcommerceapi.service;

import java.util.List;
import java.util.Optional;

import br.com.wswork.bestcommerceapi.exception.AddressNotFoundException;
import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	private final AddressRepository addressRepository;

	@Autowired
	public AddressService (AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public List<Address> getAllAddresses() {

		return addressRepository.findAll();
	}

	public Address getAddressById(Long id) {

		Optional<Address> address = addressRepository.findById(id);

		return address.orElseThrow(() -> new AddressNotFoundException("Adress with id " + id + " not found."));
	}

	public Address addAddress(Address newAddress) {

		Optional<Address> existingAddress = addressRepository.findFullAddress(newAddress.getNumber(),
																			  newAddress.getNeighbourhood(),
																			  newAddress.getState(),
																			  newAddress.getCity(),
																			  newAddress.getCountry());

		if (existingAddress.isPresent())
			throw new AddressNotFoundException("Adress already exits.");

		addressRepository.save(newAddress);

		return newAddress;
	}

	public Address modifyAddress(Address modifiedAddress, Long id) {

		Optional<Address> address = addressRepository.findById(id);

		if (address.isEmpty())
			throw new AddressNotFoundException("Adress with id " + id + " not found.");

		address.get().setId(modifiedAddress.getId());
		address.get().setNumber(modifiedAddress.getNumber());
		address.get().setNeighbourhood(modifiedAddress.getNeighbourhood());
		address.get().setState(modifiedAddress.getState());
		address.get().setCity(modifiedAddress.getCity());
		address.get().setCountry(modifiedAddress.getCountry());

		addressRepository.save(address.get());

		return address.get();
	}

	public void deleteAddressById(Long id) {

		Optional<Address> address = addressRepository.findById(id);

		if (address.isEmpty())
			throw new AddressNotFoundException("Address with id " + id + " not found.");

		addressRepository.deleteById(id);
    }
}