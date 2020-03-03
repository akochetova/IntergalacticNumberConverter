package galaxy.guide.merchant;

public class NotValidFormatException extends Exception {
	
	private String message;
	NotValidFormatException(String message){
		setMessage(message);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
