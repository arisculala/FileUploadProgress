/**
 * 
 */
package fileupload.progress;

import org.apache.commons.fileupload.ProgressListener;

/**
 * @author arisculala
 *
 */
public class FileUploadProgressListener implements ProgressListener {

	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int whichItem = 0;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		if(contentLengthKnown) {
			percentDone = (int) Math.round(100.00 * theBytesRead / theContentLength);
		}
		System.out.println(getMessage());
	}

	public String getMessage() {
		if(theContentLength == -1) {
			return "" + theBytesRead + " of unknown - Total bytes have been read.";
		} else {
			return "" + theBytesRead + " of " + theContentLength + " bytes have been read ("+percentDone+" % done).";
		}
	}

	/**
	 * @return the theBytesRead
	 */
	public long getTheBytesRead() {
		return theBytesRead;
	}

	/**
	 * @param theBytesRead the theBytesRead to set
	 */
	public void setTheBytesRead(long theBytesRead) {
		this.theBytesRead = theBytesRead;
	}

	/**
	 * @return the theContentLength
	 */
	public long getTheContentLength() {
		return theContentLength;
	}

	/**
	 * @param theContentLength the theContentLength to set
	 */
	public void setTheContentLength(long theContentLength) {
		this.theContentLength = theContentLength;
	}

	/**
	 * @return the whichItem
	 */
	public int getWhichItem() {
		return whichItem;
	}

	/**
	 * @param whichItem the whichItem to set
	 */
	public void setWhichItem(int whichItem) {
		this.whichItem = whichItem;
	}

	/**
	 * @return the percentDone
	 */
	public int getPercentDone() {
		return percentDone;
	}

	/**
	 * @param percentDone the percentDone to set
	 */
	public void setPercentDone(int percentDone) {
		this.percentDone = percentDone;
	}

	/**
	 * @return the contentLengthKnown
	 */
	public boolean isContentLengthKnown() {
		return contentLengthKnown;
	}

	/**
	 * @param contentLengthKnown the contentLengthKnown to set
	 */
	public void setContentLengthKnown(boolean contentLengthKnown) {
		this.contentLengthKnown = contentLengthKnown;
	}

	
}
