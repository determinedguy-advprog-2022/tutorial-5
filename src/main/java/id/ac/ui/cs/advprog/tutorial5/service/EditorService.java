package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Editor;

public interface EditorService  {
    Iterable<Editor> getListEditor();

    Editor createEditor(Editor editor);

    Editor getEditorById(int id);

    Editor updateEditor(int id, Editor editor);

    void deleteEditorById(int id);
}
