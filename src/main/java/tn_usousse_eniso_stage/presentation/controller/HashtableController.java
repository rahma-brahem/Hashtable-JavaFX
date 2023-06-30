package tn_usousse_eniso_stage.presentation.controller;

import tn_usousse_eniso_stage.model.Node;
import tn_usousse_eniso_stage.model.Table;
import tn_usousse_eniso_stage.service.Service;
public class HashtableController {

    private Service service;

    public HashtableController(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public Table getModel() {
        return getService().getTable();
    }

}
