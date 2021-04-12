package lk.prison_management.asset.punishment.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.offence.entity.Offence;
import lk.prison_management.asset.offence.entity.enums.OffenceType;
import lk.prison_management.asset.offence.service.OffenceService;
import lk.prison_management.asset.punishment.entity.enums.PunishmentType;
import lk.prison_management.asset.punishment.entity.Punishment;
import lk.prison_management.asset.punishment.service.PunishmentService;
import lk.prison_management.util.interfaces.AbstractController;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping( "/punishment" )
public class PunishmentController implements AbstractController< Punishment, Integer > {
  private final PunishmentService punishmentService;
  private final OffenceService offenceService;

  public PunishmentController(PunishmentService punishmentService, OffenceService offenceService) {
    this.punishmentService = punishmentService;
    this.offenceService = offenceService;
  }


  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("punishments", punishmentService.findAll());
    return "punishment/punishment";
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("punishmentDetail", punishmentService.findById(id));
    return "punishment/punishment-detail";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("punishmentTypes", PunishmentType.values());
    model.addAttribute("punishment", new Punishment());
    return "punishment/addPunishment";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("addStatus", false);
    model.addAttribute("punishmentTypes", PunishmentType.values());
    model.addAttribute("punishment", punishmentService.findById(id));
    return "punishment/addPunishment";
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Punishment punishment, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("addStatus", true);
      model.addAttribute("punishmentTypes", PunishmentType.values());
      model.addAttribute("punishment", punishment);
      return "punishment/addPunishment";
    }
    redirectAttributes.addFlashAttribute("punishmentDetail", punishmentService.persist(punishment));
    return "redirect:/punishment";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    punishmentService.delete(id);
    return "redirect:/punishment";
  }

  @GetMapping( "/offence/{id}" )
  @ResponseBody
  public MappingJacksonValue findByOffence(@PathVariable( "id" ) Integer id) {
    Offence offence = offenceService.findById(id);
    MappingJacksonValue mappingJacksonValue;
    if ( offence.getOffenceType().equals(OffenceType.FSO) ) {
      //first punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.MAP));
    } else if ( offence.getOffenceType().equals(OffenceType.SSO) ) {
      //second punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.MIP));
    } else if ( offence.getOffenceType().equals(OffenceType.SD) ) {
      //third punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.SDP));
    } else {
      mappingJacksonValue = new MappingJacksonValue(punishmentService.findAll());
    }

    SimpleBeanPropertyFilter simpleBeanPropertyFilterOne = SimpleBeanPropertyFilter
        .filterOutAllExcept("id", "name");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Punishment", simpleBeanPropertyFilterOne);

    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }

  @GetMapping( "/offence/{offenceType}" )
  @ResponseBody
  public MappingJacksonValue findByOffenceType(@PathVariable( "offenceType" ) String offenceTypeF) {
    OffenceType offenceType = OffenceType.valueOf(offenceTypeF);

    MappingJacksonValue mappingJacksonValue;
    if ( offenceType.equals(OffenceType.FSO) ) {
      //first punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.MAP));
    } else if ( offenceType.equals(OffenceType.SSO) ) {
      //second punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.MIP));
    } else if ( offenceType.equals(OffenceType.SD) ) {
      //third punishment
      mappingJacksonValue =
          new MappingJacksonValue(punishmentService.findByPunishmentType(PunishmentType.SDP));
    } else {
      mappingJacksonValue = new MappingJacksonValue(punishmentService.findAll());
    }
    SimpleBeanPropertyFilter simpleBeanPropertyFilterOne = SimpleBeanPropertyFilter
        .filterOutAllExcept("id", "name");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Punishment", simpleBeanPropertyFilterOne);

    mappingJacksonValue.setFilters(filters);
    return mappingJacksonValue;
  }
}
