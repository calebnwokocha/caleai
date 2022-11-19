/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package cml;

import java.util.ArrayList;

public class Multitask {
    private ArrayList<Network> networks;
    private Double[][] errorMeanMean;
    private int lastNetworkIndex;

    public Multitask () {}

    public Multitask (ArrayList<Network> networks) { this.networks = networks; }

    public int getTaskCount() { return networks.size(); }

    public void setNetworks(ArrayList<Network> networks) { this.networks = networks; }

    public ArrayList<Network> getNetworks() { return this.networks; }

    public void addNetwork (Network network) {
        this.transferKnowledgeTo(network);
        this.networks.add(network);
    }

    public void removeNetwork (int index) { this.networks.remove(index); }

    public void setCoverage (Double coverage) {
        for (Network network : this.networks) { network.setCoverage(coverage); }
    }

    public void setCoverage (Double[] coverage) {
        for (Network network : this.networks) { network.setCoverage(coverage); }
    }

    public void setCoverage (Double[][] coverage) {
        for (Network network : this.networks) { network.setCoverage(coverage); }
    }

    public void setCoverage (Double[][][] coverage) {
        for (int i = 0; i < this.networks.size(); i++) { networks.get(i).setCoverage(coverage[i]); }
    }

    public Double[][][] getCoverage () {
        Double[][][] coverage = new Double[this.networks.size()][][];
        for (int i = 0; i < coverage.length; i++) {
            coverage[i] = networks.get(i).getCoverage();
        } return coverage;
    }

    public Double[][] getThesis () {
        Double[][] thesis = new Double[this.networks.size()][];
        for (int i = 0; i < thesis.length; i++) {
            Network currentNetwork = networks.get(i);
            int outputLayerIndex = currentNetwork.getLength() - 1;
            thesis[i] = currentNetwork.getThesis()[outputLayerIndex];
        } return thesis;
    }

    public void train (int iteration, double[] objective, Double[]... input) {
        this.networks.get(this.lastNetworkIndex).train(iteration, objective, input);
    }

    public void test (Double[]... input) { for (Network network : this.networks) { network.test(input); } }

    private void transferKnowledgeTo(Network network) {
        if (networks.size() > 0) {
            this.lastNetworkIndex = this.networks.size() - 1;
            Double[][] lastNetworkErrorMean = networks.get(this.lastNetworkIndex).getErrorMean();
            Mean mean = new Mean();
            this.errorMeanMean = mean.powerMean(this.errorMeanMean, lastNetworkErrorMean,
                    1.0, this.networks.size());
            network.setErrorMean(this.errorMeanMean);
        }
    }
}
