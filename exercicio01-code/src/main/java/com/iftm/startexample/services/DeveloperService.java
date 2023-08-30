package com.iftm.startexample.services;

import com.iftm.startexample.models.Developer;
import com.iftm.startexample.models.dtos.DeveloperDTO;
import com.iftm.startexample.repositories.DeveloperRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository repository;

    public ResponseEntity<List<DeveloperDTO>> findAll() {
        var dbDevelopers = repository.findAll();
        if (dbDevelopers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var developerDtos = dbDevelopers.stream().map(developer -> {
            var developerDto = new DeveloperDTO(developer);
            return developerDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(developerDtos);
    }

    public ResponseEntity<DeveloperDTO> findById(ObjectId id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        var dbDeveloper = repository.findById(id);
        if (dbDeveloper.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DeveloperDTO(dbDeveloper.get()));
    }

    public ResponseEntity<DeveloperDTO> save(Developer developer) {
        if (developer.getName().isBlank() || developer.getLevel() == null) {
            return ResponseEntity.badRequest().build();
        }
        developer.setId(ObjectId.get());

        return ResponseEntity.ok(new DeveloperDTO(repository.save(developer)));
    }

    // implemente um método para inserir uma lista de objeto do tipo Developer


    public ResponseEntity<DeveloperDTO> update(DeveloperDTO developer) {
        if (developer.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var objectId = new ObjectId(developer.getId());
        var dbDeveloper = repository.findById(objectId);
        if (dbDeveloper.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var developerToUpdate = dbDeveloper.get();
        developerToUpdate.setName(developer.getName());
        developerToUpdate.setLevel(developer.getLevel());
        developerToUpdate.setLastName(developer.getLastName());
        developerToUpdate.setSpecialization(developer.getSpecialization());
        // update developer address and sector
        developerToUpdate.setAddress(developer.getAddress());
        developerToUpdate.setSector(developer.getSector());


        return ResponseEntity.ok(new DeveloperDTO(repository.save(developerToUpdate)));
    }

    public ResponseEntity<?> delete(ObjectId id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        repository.deleteById(id);

        var dbDeveloper = repository.findById(id);
        if(dbDeveloper.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        return ResponseEntity.ok().build();

    }
}