package com.company;


import javax.sound.midi.Sequencer;

class doThread extends Thread {
    private Sequencer sequencer;

    public doThread(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public void run() {
        sequencer.start();
        System.out.println("Playing music");
    }

    public void halt(Sequencer sequencer) {

        sequencer.stop();
    }
}
