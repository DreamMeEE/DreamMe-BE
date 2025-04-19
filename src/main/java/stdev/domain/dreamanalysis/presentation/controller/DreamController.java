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
@RequestMapping("/api/pic")
public class DreamController {

    private final DreamService dreamService;

    @PostMapping
    public String dreamPost2(@RequestPart("image") MultipartFile image) throws IOException {

        return dreamService.dreamPost(image);
    }

}
