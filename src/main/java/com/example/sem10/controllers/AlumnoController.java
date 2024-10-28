package com.example.sem10.controllers;

import com.example.sem10.domain.entities.Alumno;
import com.example.sem10.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @RequestMapping(value = "/listar2", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        return "listar2";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form2")
    public String crear(Map<String, Object> model) {

        Alumno alumno = new Alumno();
        model.put("alumno", alumno);
        return "form2";
    }

    @RequestMapping(value="/form2/{id}")
    public String editar(@PathVariable(value="id") Integer id, Map<String, Object> model) {

        Alumno alumno = null;

        if(id > 0) {
            alumno = alumnoService.buscar(id);
        } else {
            return "redirect:/listar2";
        }
        model.put("alumno", alumno);
        return "form2";
    }

    @RequestMapping(value = "/form2", method = RequestMethod.POST)
    public String guardar(@Valid Alumno alumno, BindingResult result, Model model, SessionStatus status) {
        if(result.hasErrors()) {
            return "form2";
        }

        alumnoService.grabar(alumno);
        status.setComplete();
        return "redirect:/listar2";
    }

    @RequestMapping(value="/eliminar2/{id}")
    public String eliminar(@PathVariable(value="id") Integer id) {

        if(id > 0) {
            alumnoService.eliminar(id);
        }
        return "redirect:/listar2";
    }

    @RequestMapping(value="/ver2")
    public String ver(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        model.addAttribute("titulo", "Lista de alumnos");

        return "alumno/ver";
    }
}
