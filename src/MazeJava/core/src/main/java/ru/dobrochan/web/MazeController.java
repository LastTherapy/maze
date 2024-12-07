package ru.dobrochan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import ru.dobrochan.model.Maze;
import ru.dobrochan.service.MazeService;

@Controller
public class MazeController {

    private final MazeService mazeService;

    @Autowired
    public MazeController(MazeService mazeService) {
        this.mazeService = mazeService;
    }

    @PostMapping("api/maze/upload")
    public ResponseEntity<Maze> loadMaze(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Maze maze = mazeService.loadMaze(reader);
        return ResponseEntity.ok(maze);
    }

    @GetMapping("api/maze/generate")
    public ResponseEntity<Maze> generateMaze(@RequestParam("width") int width, @RequestParam("height") int height) {
        Maze maze = mazeService.generateMaze(width, height);
        return ResponseEntity.ok(maze);
    }

    @GetMapping("api/download-maze")
    public ResponseEntity<byte[]> downloadMaze() {
        String answer = mazeService.getMazeString();
        byte[] fileBytes = answer.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment().filename("maze.txt").build());
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("api/string-maze")
    public ResponseEntity<String> stringMaze() {
        String answer = mazeService.getMazeString();
        return ResponseEntity.ok(answer);
    }
}
