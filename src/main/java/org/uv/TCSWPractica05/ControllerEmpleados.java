
package org.uv.TCSWPractica05;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/empleados")
public class ControllerEmpleados {
    
    @Autowired
    private RepositoryEmpleados repositoryEmpleados;
    
    @GetMapping()
    public List<Empleado> list() {
        return repositoryEmpleados.findAll();
        
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        Optional<Empleado> optionalEmpleado = repositoryEmpleados.findById(id);
        if(!optionalEmpleado.isEmpty())
            return optionalEmpleado.get();
        else
            return null;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> put(@PathVariable Long id, @RequestBody Empleado emp) {
        Optional<Empleado> optionalEmpleado = repositoryEmpleados.findById(id);
        if (optionalEmpleado.isPresent()) {
            Empleado empExistente = optionalEmpleado.get();
            empExistente.setNombre(emp.getNombre());
            empExistente.setDireccion(emp.getDireccion());
            empExistente.setTelefono(emp.getTelefono());
            Empleado empActualizado = repositoryEmpleados.save(empExistente);
            return ResponseEntity.ok(empActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   

    
    @PostMapping
    public ResponseEntity<Empleado> post(@RequestBody Empleado emp) {
        Empleado empNew = repositoryEmpleados.save(emp);
        return ResponseEntity.ok(empNew);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Empleado> optionalEmpleado = repositoryEmpleados.findById(id);
        if (optionalEmpleado.isPresent()) {
            repositoryEmpleados.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
