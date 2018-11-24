package com.s.hero.controller;

import com.s.hero.MessageHolder;
import com.s.hero.model.RequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by alpa on 11/23/18
 */
@RestController
@RequiredArgsConstructor
public class HeroController {


    @RequestMapping(value = "/response", method = RequestMethod.POST)
    public ResponseEntity requestMessageResponse(@RequestBody RequestMessage requestMessage) {
        MessageHolder.getInstance().setRequestMessage(requestMessage);
        return new ResponseEntity<>(requestMessage, HttpStatus.OK);
    }

}
