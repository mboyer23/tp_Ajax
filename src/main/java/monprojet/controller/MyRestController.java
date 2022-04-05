package monprojet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

@RestController 
@RequestMapping(path = "/rest")
@Slf4j
public class MyRestController {
	@Autowired
	private CityRepository cityDao;
	@Autowired
	private CountryRepository countryDao;

	@GetMapping(path = "/cities")
	public List<City> getAllCities() {
		// This returns a JSON or XML with the cities
		return cityDao.findAll();
	}

	/**
	 * Enregistre un pays dans la base
	 * Requête HTTP POST à l'URL http://localhost:8989/rest/saveCountry	
	 * @param lePays  le pays à enregistrer, initialisée par Spring à partir des
	 *                valeurs  transmises dans la requête POST
	 *                Spring fera une requête SQL INSERT ou UPDATE pour enregistrer
	 *                le pays dans la base
	 * @return le pays enregistré (avec sa clé) en format JSON
	 */
	@PostMapping(path = "saveCountry") 
	public Country enregistreUnPays(@RequestBody Country lePays) {
		log.info("Reçu: {}", lePays);
		countryDao.save(lePays);
		log.info("Enregistré: {}", lePays);
		return lePays;
	}

	/**
	 * Enregistre une ville dans la base
	 * Requête HTTP POST à l'URL http://localhost:8989/rest/saveCity	
	 * @param laVille la ville à enregistrer, initialisée par Spring à partir des
	 *                valeurs transmises dans la requête POST
	 *                Spring fera automatiquement une requête SQL SELECT pour
	 *                récupérer le pays à partir de son id.
	 *                Spring fera une requête SQL INSERT ou UPDATE pour enregistrer
	 *                la ville dans la base
	 * @return la ville enregistrée (avec sa clé) en format JSON
	 */
	@PostMapping(path = "saveCity") 
	public City enregistreUneVille(@RequestBody City laVille) {
		log.info("Reçu: {}", laVille);
		cityDao.save(laVille);
		log.info("Enregistré: {}", laVille);
		return laVille;
	}
}
