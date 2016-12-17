package at.jku.ce.CoMPArE.elaborate.wizardsteps;

import at.jku.ce.CoMPArE.elaborate.changeCommands.ProcessChange;
import at.jku.ce.CoMPArE.execute.Instance;
import at.jku.ce.CoMPArE.process.Subject;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by oppl on 16/12/2016.
 */
public class ElaborationStep implements WizardStep {

    protected Wizard owner;

    protected Instance instance;
    protected Subject subject;
    protected String caption;

    private boolean canAdvance;
    private boolean canGoBack;

    private ElaborationStep nextStep;

    protected List<ProcessChange> processChanges;

    protected VerticalLayout fLayout;

    public ElaborationStep(Wizard owner, Subject s, Instance i) {
        subject = s;
        instance = i;
        this.owner = owner;
        setCanAdvance(false);
        setCanGoBack(true);
        nextStep = null;
        processChanges = new LinkedList<>();
        fLayout = new VerticalLayout();
        fLayout.setMargin(true);
        fLayout.setSpacing(true);
        caption = new String("");
    }


    @Override
    public final String getCaption() {
        return caption;
    }

    @Override
    public final Component getContent() {
        return fLayout;
    }

    @Override
    public final boolean onAdvance() {
        if (!canAdvance) Notification.show("Please provide the necessary information first!", Notification.Type.WARNING_MESSAGE);
        return canAdvance;

    }

    @Override
    public final boolean onBack() {
        return canGoBack;
    }

    public List<ProcessChange> getProcessChanges() {
        return processChanges;
    }

    protected final void removeNextSteps() {
        if (nextStep!=null) {
            nextStep.removeNextSteps();
            owner.removeStep(nextStep);
            nextStep = null;
            setCanAdvance(canAdvance);
        }
    }

    protected final void removeParticularFollowingStep(ElaborationStep step) {
        if (nextStep!=null) {
            if (step == nextStep) {
                nextStep = step.nextStep;
                owner.removeStep(step);
            }
            else {
                nextStep.removeParticularFollowingStep(step);
            }
            setCanAdvance(canAdvance);

        }
    }

    protected final void addNextStep(ElaborationStep step) {
        if (step == null) return;
        if (nextStep == null) {
            nextStep = step;
            owner.addStep(nextStep);
        }
        else {
            nextStep.addNextStep(step);
        }
        setCanAdvance(canAdvance);
    }

    protected final void setCanAdvance(boolean canAdvance) {
        this.canAdvance = canAdvance;
        if (!canAdvance) {
            owner.getFinishButton().setEnabled(false);
            owner.getNextButton().setEnabled(false);
        }
        else {
            if (nextStep == null) {
                owner.getFinishButton().setEnabled(true);
                owner.getNextButton().setEnabled(false);
            }
            else {
                owner.getFinishButton().setEnabled(false);
                owner.getNextButton().setEnabled(true);
            }
        }
    }

    protected final void setCanGoBack(boolean canGoBack) {
        this.canGoBack = canGoBack;
        owner.getBackButton().setEnabled(canGoBack);
    }
}