package br.com.controller;

import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.model.Doc;
import br.com.service.UploadFileService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UploadFileController {

	private final UploadFileService uploadFileService;

	@GetMapping("/")
	public String get(Model model) {
		model.addAttribute("docs",uploadFileService.getFiles().stream().collect(Collectors.toList()));
		return "doc";
	}

	@PostMapping("/uploadFiles")
	public String uploadMultiFiles(@RequestParam MultipartFile[] files) {
		for (MultipartFile file : files) {
			uploadFileService.saveFile(file);
		}
		return "redirect:/";
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
		Doc doc = uploadFileService.getFile(fileId).get();
		System.out.println("data:"+doc.getData());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(doc.getDocType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() +"\"")
				.body(new ByteArrayResource(doc.getData()));
	}

}
