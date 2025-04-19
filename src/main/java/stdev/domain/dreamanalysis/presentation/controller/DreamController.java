package stdev.domain.dreamanalysis.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stdev.domain.dreamanalysis.application.DreamService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/api/dream")
public class DreamController {

    private final DreamService dreamService;

    //@PostMapping
    public void dreamPost(    @RequestPart("comment") String comment,
                              @RequestPart("image") MultipartFile image) throws IOException {

        dreamService.dreamPost(comment, image);
    }

}
