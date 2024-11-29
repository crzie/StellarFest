package views;

public interface Page {
	void setLayouts();
	void setStyles();
	void setEvents();
	
	default void initializePage() {
		setLayouts();
		setStyles();
		setEvents();
	}
}
