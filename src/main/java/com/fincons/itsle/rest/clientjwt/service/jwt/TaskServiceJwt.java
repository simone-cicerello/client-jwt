package com.fincons.itsle.rest.clientjwt.service.jwt;

import com.fincons.itsle.rest.clientjwt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceJwt {
    //TODO merge TaskService with TaskServiceJwt -> dispatch dynamically UserService and UserServiceJwt methods

    @Autowired
    private UserServiceJwt userServiceJwt;

    public void taskOne(){
        //Stampa a video del numero di utenti presenti sul DB
        log.info("Utenti sul DB: {}", userServiceJwt.findAll().size());
        //50 utenti
    }

    public void taskTwo(){
        //Inserire la propria anagrafica nel DB e stampare l’id generato ad avvenuto inserimento.
        User myUser = new User("Simone", "Cicerello", "simone.cicerello@finconsgroup.com");

        var myId = userServiceJwt.createUser(myUser).getId();
        log.info("My id: {}", myId);

        User myRetrievedUser = userServiceJwt.findOneById(myId);
        //Reperire la propria anagrafica e stamparla in uppercase.
        log.info("My datas: {} {} {}", myRetrievedUser.getFirstName().toUpperCase(), myRetrievedUser.getLastName().toUpperCase(), myRetrievedUser.getEmail().toUpperCase());
    }

    public void taskThree(){
        //Modificare le anagrafiche degli utenti la cui email NON ha dominio «.com».
        List<User> usersNotDomainCom = userServiceJwt.findAll().stream().toList();
        //50 utenti

        List<User> editedUsers = usersNotDomainCom.stream()
                .filter(u -> !u.getEmail().contains(".com"))
                .map(u -> {
                    u.setId(u.getId());
                    //Convertire nome e cognome in Uppercase;
                    u.setFirstName(u.getFirstName().toUpperCase());
                    u.setLastName(u.getLastName().toUpperCase());
                    //Trasformare il dominio in «itslecce.com» mantenendo invariato il resto dell’email;
                    u.setEmail(u.getEmail().replace(u.getEmail().split("@")[1], "itslecce.com"));
                    return u;
                })
                .toList();
        //19 utenti

        log.info("Filtered and edited {} items", editedUsers.size());

        editedUsers.forEach(
              u ->  {
                  var user = userServiceJwt.updateUser(u);
                  //Stampare a video gli elementi modificati.
                  log.debug("Updated: {} {} {} {}", user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
              }
        );
    }

    public void taskFour(){
        //Eliminare dal DB tutti gli utenti che hanno l’iniziale del nome uguale all’iniziale del cognome.
        List<User> allUsers = userServiceJwt.findAll().stream().toList();
        List<User> filteredUsers = allUsers.stream()
                .filter(u -> u.getFirstName().charAt(0) == u.getLastName().charAt(0))
                .toList();
        //2 utenti
        filteredUsers.forEach(
                u -> {
                    //Stampare a video nome e cognome degli utenti in questione
                    log.debug("{} {}", u.getFirstName(), u.getLastName());
                    userServiceJwt.deleteUser(u.getId());
                }
        );
    }


}
