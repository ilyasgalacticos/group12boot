package kz.iitu.swd.group34.FirstSpringBootIITU.rest;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Items;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class MainRestController {

    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping(path = "/allitems")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Items>> getAllItems(){
        List<Items> items = itemsRepository.findAllByDeletedAtNull();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(path = "/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> deleteItem(@RequestParam(name = "id") Long id){
        Items item = itemsRepository.findByIdAndDeletedAtNull(id).get();
        item.setDeletedAt(new Date());
        itemsRepository.save(item);
        return new ResponseEntity<>("Item deleted", HttpStatus.OK);
    }

}
