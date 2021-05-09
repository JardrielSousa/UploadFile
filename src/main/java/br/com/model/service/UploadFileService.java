package br.com.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.model.Doc;
import br.com.model.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadFileService {
	private final UploadFileRepository uploadFileRepository;
	
	public Doc saveFile(MultipartFile file) {
		String docName = file.getOriginalFilename();
		try {
			Doc doc = new Doc(null, docName,file.getContentType(),file.getBytes());
			return uploadFileRepository.save(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Doc> getFile(Integer fileId){
		return uploadFileRepository.findById(fileId);
	}
	
	public List<Doc> getFiles(){
		return uploadFileRepository.findAll();
	}
}
