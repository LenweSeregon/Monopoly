package utils;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound_player {

	private final int BUFFER_SIZE = 128000;
	private URL fichierSon;
	private AudioInputStream streamAudio;
	private AudioFormat formatAudio;
	private SourceDataLine sourceLine;

	private int nBytesRead;

	private boolean stop = false;
	private boolean boucle = true;

	/**
	 * @param filename
	 *            the name of the file that is going to be played
	 */
	public Sound_player(String fichier, boolean boucle) {

		try {
			fichierSon = this.getClass().getResource(fichier);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			streamAudio = AudioSystem.getAudioInputStream(fichierSon);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.boucle = boucle;

		formatAudio = streamAudio.getFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(formatAudio);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		sourceLine.start();
	}

	/**
	 * Méthode permettant d'arreter le son
	 */
	public void stopper() {
		this.stop = true;
	}

	/**
	 * Méthode permettant de jouer le son
	 */
	public void play() {
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1 && !stop) {
			try {
				nBytesRead = streamAudio.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			} else if (this.boucle) {
				try {
					streamAudio = AudioSystem.getAudioInputStream(fichierSon);
					nBytesRead = 0;
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		sourceLine.drain();
		sourceLine.close();
	}

}