package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.Console;
import pl.sdacademy.gamechanger.model.dto.NewConsoleDTO;
import pl.sdacademy.gamechanger.repository.AuctionRepository;
import pl.sdacademy.gamechanger.repository.CategoryRepository;
import pl.sdacademy.gamechanger.repository.ConsoleRepository;
import pl.sdacademy.gamechanger.repository.ItemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuctionRepository auctionRepository;
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
    public void deleteById(Long id){
        consoleRepository.deleteById(id);
    }
    public Optional<Console> findById (Long id){
        return consoleRepository.findById(id);
    }
    public List<Console> findAllByNameContainingString(String name){
        return consoleRepository.findAllByNameContaining(name);
    }
    public List<Console> findByDateOfProductionGreaterThanAndLessThan(LocalDate min, LocalDate max){
        return consoleRepository.findAllByDateOfProductionIsBetween(min, max);
    }
    public List<Console> findNew(){
        return consoleRepository.findAllByIsNewIsTrue();
    }
    public List<Console> findUsed(){
        return consoleRepository.findAllByIsNewIsFalse();
    }
    public List<Console> findWithWarranty(){
        return consoleRepository.findAllByIsWarrantyIsTrue();
    }

    public List<Console> findByHardDriveGreaterThanAndLessThan(Long min, Long max){
        return consoleRepository.findAllByHardDriveGbIsBetween(min, max);
    }

    public List<Console> findAll(){
        return consoleRepository.findAll();
    }

    public void updateConsole(Console console){
        Optional<Console> optionalLaptop = consoleRepository.findById(console.getId());

        if (optionalLaptop.isPresent()) {

            Console updateConsole = optionalLaptop.get();
            if (console.getName() != null){
                updateConsole.setName(console.getName());
            }

            if (console.getHardDriveGb() != null){
                updateConsole.setHardDriveGb(console.getHardDriveGb());
            }
            if (console.getIsNew() !=null){
                updateConsole.setIsNew(console.getIsNew());
            }
            if (console.getIsWarranty() != null){
                updateConsole.setIsWarranty(console.getIsWarranty());
            }

            if (console.getDateOfProduction() != null){
                updateConsole.setDateOfProduction(console.getDateOfProduction());
            }
            consoleRepository.save(updateConsole);
        }
    }

    public Optional<Console> getConsoleWithId(Long laptopId) {
        return consoleRepository.findById(laptopId);
    }

}
