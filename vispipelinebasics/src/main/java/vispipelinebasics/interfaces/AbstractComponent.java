/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vispipelinebasics.interfaces;

import java.io.Serializable;
import vispipelinebasics.exception.VisPipelineException;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public interface AbstractComponent<I extends InputInterface, O extends OutputInterface> extends Serializable {

    public O execute(I input) throws VisPipelineException;

}
