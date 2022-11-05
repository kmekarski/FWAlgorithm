package org.example.controller;


import org.example.view.FWview;
import org.example.model.FWmodel;

public class FWcontroller {







    /**
     * Main program void.
     * @param args list of command line arguments.
     * agrs:
     * 1. first sum component
     * 2. second sum component
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Controller starts");
        FWview view = new FWview();
        FWmodel model = new FWmodel();
        int size;
        if (args.length != 1)
        {
            size = view.wrongArgc();
        }
        else
        {
            size = Integer.parseInt(args[0]);
        }

        try
        {
            FWmodel.checkSize(size);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        view.GUI(size);

        if(view.resultReady()) {
            model.setGraph(view.getCreatedGraph());
        }

        model.FloydWarshall();

        view.showGraph(model.getSolvedGraph());
    }
}
