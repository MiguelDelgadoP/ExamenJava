package com.mx.UsuariosCrud.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.mx.UsuariosCrud.entidades.Usuario;
import com.mx.UsuariosCrud.servicios.UsuarioService;
import com.mx.UsuariosCrud.util.paginacion.PageRender;
import com.mx.UsuariosCrud.util.reportes.UsuarioExporterExcel;
import com.mx.UsuariosCrud.util.reportes.UsuarioExporterPDF;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Usuario usuario = usuarioService.findOne(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El usuario no existe");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Detalle usuario: " + usuario.getNombre());
		return "ver";
	}

	@GetMapping({ "/", "/listar", "" })
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
		String palabraClave = "maria";
		String activo = "A";
		String inactivo = "B";
		String revocado = "R";
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		@SuppressWarnings("unused")
		List<Usuario> listarUsuario = usuarioService.findAll(palabraClave);
		@SuppressWarnings("unused")
		List<Usuario> listarActivos = usuarioService.findAllActivo(activo);
		@SuppressWarnings("unused")
		List<Usuario> listarInactivos = usuarioService.findAllInactivo(inactivo);
		@SuppressWarnings("unused")
		List<Usuario> listarRevocados = usuarioService.findAllRevocado(revocado);
		PageRender<Usuario> pageRender = new PageRender<>("/listar", usuarios);

		modelo.addAttribute("titulo", "Listado de usuarios");
		modelo.addAttribute("usuarios", usuarios);
		modelo.addAttribute("page", pageRender);

		return "listar";
	}

	@GetMapping("/form")
	public String mostrarFormularioDeRegistrarUsuario(Map<String, Object> modelo) {
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		modelo.put("titulo", "Registro de usuarios");
		return "form";
	}

	@PostMapping("/form")
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model modelo, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro del usuario");
			return "form";
		}

		String mensaje = (usuario.getId() != null) ? "El usuario ha sido editado exitosamente" : "Usuario registrado";

		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar";
	}

	@GetMapping("/form/{id}")
	public String editarusuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Usuario usuario = null;
		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero");
			return "redirect:/listar";
		}

		modelo.put("usuario", usuario);
		modelo.put("titulo", "Edición de usuario");
		return "form";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario eliminado con exito");
		}
		return "redirect:/listar";
	}

	@GetMapping("/exportarPDF")
	public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";

		response.setHeader(cabecera, valor);

		List<Usuario> empleados = usuarioService.findAll();

		UsuarioExporterPDF exporter = new UsuarioExporterPDF(empleados);
		exporter.exportar(response);
	}

	@GetMapping("/exportarExcel")
	public void exportarListadoDeUsuariosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";

		response.setHeader(cabecera, valor);

		List<Usuario> usuarios = usuarioService.findAll();

		UsuarioExporterExcel exporter = new UsuarioExporterExcel(usuarios);
		exporter.exportar(response);
	}

}
