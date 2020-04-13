package org.easyweb4j.web.core.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class CommonControllerAdvice {


}
