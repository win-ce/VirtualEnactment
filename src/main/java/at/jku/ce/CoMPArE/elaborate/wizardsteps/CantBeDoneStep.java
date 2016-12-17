package at.jku.ce.CoMPArE.elaborate.wizardsteps;

import at.jku.ce.CoMPArE.execute.Instance;
import at.jku.ce.CoMPArE.process.State;
import at.jku.ce.CoMPArE.process.Subject;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import org.vaadin.teemu.wizards.Wizard;

/**
 * Created by oppl on 16/12/2016.
 */
public class CantBeDoneStep extends ElaborationStep {

    public CantBeDoneStep(Wizard owner, Subject s, Instance i) {
        super(owner, s, i);

        State state = instance.getAvailableStateForSubject(subject);
        caption = new String("\"" + state + "\" can't be done at the moment.");

        final Label questionPrompt = new Label("\"" + state + "\" can't be done at the moment.");

        final OptionGroup answerOptions = new OptionGroup("Why?");
        final String option1 = new String("I need to do something else first.");
        final String option2 = new String("I need more input to be able to do this activity.");

        answerOptions.addValueChangeListener(e -> {
            setCanAdvance(true);
            String selection = (String) answerOptions.getValue();
            if (selection != null) {
                removeNextSteps();
                ElaborationStep step = null;
                if (selection.equals(option1)) step = new SomeThingElseFirstStep(owner, subject, instance);
                if (selection.equals(option2)) step = new NeedMoreInputStep(owner, subject, instance);
                addNextStep(step);
            }
        });

        answerOptions.addItem(option1);
        answerOptions.addItem(option2);

        fLayout.addComponent(questionPrompt);
        fLayout.addComponent(answerOptions);
    }
}