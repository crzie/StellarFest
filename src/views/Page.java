package views;

public interface Page {
	// implement this interface for every page created
	void setLayouts();
	void setStyles();
	void setEvents();
	
	// To do: call initializePage() on every page constructor
	// example:
	// public RegisterPage() {
	//     initializePage();
	// }
	default void initializePage() {
		setLayouts();
		setStyles();
		setEvents();
	}
}
