package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.Console;
import pl.sdacademy.gamechanger.model.dto.NewConsoleDTO;
import pl.sdacademy.gamechanger.repository.ConsoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    public List<Console> getAllConsoles(){
        return consoleRepository.findAll();
    }

    public Console addConsole(NewConsoleDTO newConsoleDTO){

        Console console = new Console();
        console.setName(newConsoleDTO.getName());
        console.setHardDriveGb(newConsoleDTO.getHardDriveGB());
        console.setDateOfProduction(newConsoleDTO.getDateOfProduction());
        console.setIsNew(newConsoleDTO.getIsNew());
        console.setIsWarranty(newConsoleDTO.getIsWarranty());

        return consoleRepository.save(console);
    }
    public Optional<Console> findById (Long id){
        return consoleRepository.findById(id);
    }
}
