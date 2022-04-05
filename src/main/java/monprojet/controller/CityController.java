package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/cities") // This means URL's start with /cities (after Application path)
@Slf4j
public class CityController {

	// On affichera par défaut la page 'cities.mustache'
	private static final String DEFAULT_VIEW = "cities";

	@Autowired
	private CityRepository cityDao;
	@Autowired
	private CountryRepository countryDao;

	/**
	 * Affiche la page d'édition des villes
	 * 
	 * @param model Les infos transmises à la vue (injecté par Spring)
	 * @return le nom de la vue à afficher
	 */
	@GetMapping(path = "show") // à l'URL http://localhost:8989/cities/show
	public String montreLesVilles(Model model) {
		log.info("On affiche les villes");
		// On initialise la ville avec des valeurs par défaut
		Country france = countryDao.findById(1).orElseThrow();
		City nouvelle = new City("Nouvelle ville", france);
		nouvelle.setPopulation(50);
		model.addAttribute("city", nouvelle);
		model.addAttribute("cities", cityDao.findAll());
		model.addAttribute("countries", countryDao.findAll());
		return DEFAULT_VIEW;
	}

	/**
	 * Affiche la page pour modifier une ville
	 * 
	 * @param model   Les infos transmises à la vue (injecté par Spring)
	 * @param laVille La requête GET contient l'id de la ville à modifier,
	 *                Spring fait une requête SQL pour récupérer la ville par son
	 *                id.
	 * @return le nom de la vue à afficher
	 */
	@GetMapping(path = "edit") // à l'URL http://localhost:8989/cities/show
	public String editeUneVille(@RequestParam("id") City laVille, Model model) {
		log.info("On modifie la ville {}", laVille);
		model.addAttribute("city", laVille);
		model.addAttribute("cities", cityDao.findAll());
		model.addAttribute("countries", countryDao.findAll());
		return DEFAULT_VIEW;
	}

	/**
	 * Supprime une ville
	 * 
	 * @param model Les infos transmises à la vue (injecté par Spring)
	 * @param id    l'id de la ville à supprimer,
	 * @return une redirection vers la page d'affichage des villes
	 */
	@GetMapping(path = "delete") // à l'URL http://localhost:8989/cities/show
	public String supprimeUneVille(Integer id, Model model) {
		log.info("On supprime la ville n° {}", id);
		cityDao.deleteById(id);
		// On redirige vers la page de liste des villes
		return "redirect:/cities/show";
	}

	/**
	 * Enregistre une ville dans la base
	 * 
	 * @param laVille la ville à enregistrer, initialisée par Spring à partir des
	 *                valeurs soumises dans le formulaire
	 *                Spring fera automatiquement une requête SQL SELECT pour
	 *                récupérer le pays à partir de son id.
	 *                Spring fera une requête SQL INSERT ou UPDATE pour enregistrer
	 *                la ville dans la base
	 * @return une redirection vers la page d'affichage des villes
	 */
	@PostMapping(path = "save") // Requête HTTP POST à l'URL http://localhost:8989/cities/save
	public String enregistreUneVille(City laVille) {
		cityDao.save(laVille);
		log.info("La ville {} a été enregistrée", laVille);
		// On redirige vers la page de liste des villes
		return "redirect:/cities/show";
	}
}
