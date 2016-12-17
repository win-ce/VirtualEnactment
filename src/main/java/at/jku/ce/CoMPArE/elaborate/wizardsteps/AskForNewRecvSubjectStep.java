package at.jku.ce.CoMPArE.elaborate.wizardsteps;

import at.jku.ce.CoMPArE.elaborate.changeCommands.ProcessChangeCommand;
import at.jku.ce.CoMPArE.execute.Instance;
import at.jku.ce.CoMPArE.process.Subject;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.vaadin.teemu.wizards.Wizard;

import java.util.List;

/**
 * Created by oppl on 17/12/2016.
 */
public class AskForNewRecvSubjectStep extends ElaborationStep {

    final Label questionPrompt;
    final TextField inputField;
    final String newMessage;

    public AskForNewRecvSubjectStep(Wizard owner, String input, Subject s, Instance i) {
        super(owner, s, i);

        caption = new String("I can get this input from somebody else.");
        questionPrompt = new Label("I can get this input from somebody else.");
        inputField = new TextField("Whom do you get this input from?");
        newMessage = input;

        inputField.addValueChangeListener(e -> {
            if (inputField.getValue().equals("")) setCanAdvance(false);
            else setCanAdvance(true);
        });

        fLayout.addComponent(questionPrompt);
        fLayout.addComponent(inputField);

    }

    @Override
    public List<ProcessChangeCommand> getProcessChanges() {
        Subject newSubject = new Subject(inputField.getValue());
        //TODO: create according Command: insertNewSubject(newSubject, instance);
        //TODO: create according Command: insertNewReceiveState(newMessage, newSubject, subject, instance, true);
        return processChanges;
    }
}
