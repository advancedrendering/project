package templates;

public class TimeFPSCounter {
	
	private boolean enabled = true;
	
	private long timePassedNanos = 0;
	private long lastTime = -1; //Previous time

	private long accumulatedTimeNanos = 0;

	/* FPS calculation */
	private float fps = 60;
	private int frames = 0;
	private long firstFrameTime = 0;
	public int fpsRefreshTimeNanos = 500 * 1000 * 1000; //each 500ms


	/*************************
	 * NEW *   updateTime    *
	 *******************************************************************************
	 * This method calculates the time that OpenGl takes to draw frames.           *
	 * This time should be used to increase the movement of the objects of the     *
	 * scene.                                                                      *
	 *******************************************************************************/
	public void update() {
	    //Counter enabled ?
	    if(!enabled) return;

	    if(lastTime == -1) {
	        //Initialization of the counter
	        lastTime = System.nanoTime();
	        timePassedNanos = 0;

	        //Initialization for FPS calculation
	        fps = 0;
	        frames = 0;
	        firstFrameTime = lastTime;
	    }
	    else {
	        //Get the current time
	        long currentTime = System.nanoTime();
	        //Time passed
	        timePassedNanos = currentTime-lastTime;
	        //Update last time, it is now the current for next frame calculation
	        lastTime = currentTime;
	        //Accumulate time
	        if(Blocks.animationActive)
	        	accumulatedTimeNanos += timePassedNanos;

	        //FPS
	        frames++;


//	        System.out.println(getAccumulatedTimePassedMillis());
	        //Calculate fps
	        long dt = currentTime-firstFrameTime;
	        if(dt >= fpsRefreshTimeNanos) {
	            fps = (float)(1000*frames)/(float)(dt / 1000000);
		        System.out.println("fps "+fps);
	            frames = 0;
	            firstFrameTime = currentTime;
	        }
	    }
	}

	/** @return the time passed, in milliseconds, to render last frame */
	public final long getTimePassedMillis() {
	    return timePassedNanos / 1000000;
	}

	/** @return the time passed, in microseconds, to render last frame */
	public final long getTimePassedMicros() {
	    return timePassedNanos / 1000;
	}

	/** @return the time passed, in nanoseconds, to render last frame */
	public final long getTimePassedNanos() {
	    return timePassedNanos;
	}
	
	/** @return the time passed, in milliseconds */
	public final long getAccumulatedTimePassedMillis() {
	    return accumulatedTimeNanos / 1000000;
	}

	/** @return the time passed, in microseconds */
	public final long getAccumulatedTimePassedMicros() {
	    return accumulatedTimeNanos / 1000;
	}

	/** @return the time passed, in nanoseconds */
	public final long getAccumulatedTimePassedNanos() {
	    return accumulatedTimeNanos;
	}

	/** @return the number of frames per seconds */
	public final float getFPS() {
	    return fps;
	}

	public void resetAccumulatedTime() {
		this.accumulatedTimeNanos = 0;		
	}
}
