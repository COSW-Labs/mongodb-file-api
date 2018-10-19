package eci.cosw.controller;


import com.mongodb.client.gridfs.model.GridFSFile;
//import com.mongodb.gridfs.GridFSFile;
import eci.cosw.data.TodoRepository;
import eci.cosw.data.model.Todo;
import eci.cosw.data.service.TodoServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequestMapping("api")
@RestController
public class RESTController {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    TodoServiceImpl todoService;
    
    @RequestMapping("/files/{filename}")
    public ResponseEntity<InputStreamResource> getFileByName(@PathVariable String filename) throws IOException {

        GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
        System.out.println("------------->Nombre de Archivo: " +filename);
        System.out.println("------------->Archivo: " +file.getFilename());
        System.out.println("------------->Metadata de Archivo: " +file.getMetadata());
        if (file == null) {
            System.out.println("------------->File es null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("------------->Entra al segundo condicional");
            GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
            System.out.println("------------->Obtiene el recurso");
            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(resource.getContentType()))
                .body(new InputStreamResource(resource.getInputStream()));
        }

    }

    @CrossOrigin("*")
    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        return null;
    }

    @CrossOrigin("*")
    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        try {
            this.todoService.addTodo(todo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin("*")
    @GetMapping("/todo")
    public List<Todo> getTodoList() {
        return this.todoService.getTodoList();
    }

}
