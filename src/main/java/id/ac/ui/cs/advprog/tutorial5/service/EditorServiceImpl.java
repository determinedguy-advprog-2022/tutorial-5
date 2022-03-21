package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Article;
import id.ac.ui.cs.advprog.tutorial5.model.Editor;
import id.ac.ui.cs.advprog.tutorial5.repository.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorRepository editorRepository;

    @Override
    public Editor createEditor(Editor editor) {
        editor.setRegisteredAt(new Date());
        editorRepository.save(editor);
        return editor;
    }

    @Override
    public Editor getEditorById(int id) {
        return editorRepository.findById(id);
    }

    @Override
    public Iterable<Editor> getListEditor() {
        return editorRepository.findAll();
    }

    @Override
    public Editor updateEditor(int id, Editor editor) {
        // Get the old author to set created date
        Editor oldAuthor = getEditorById(id);

        editor.setId(id);
        editor.setRegisteredAt(oldAuthor.getRegisteredAt());
        editorRepository.save(editor);
        return editor;
    }

    @Override
    public void deleteEditorById(int id) {
        Editor editor = editorRepository.findById(id);
        if(editor != null)
            editorRepository.delete(editor);
    }
}
