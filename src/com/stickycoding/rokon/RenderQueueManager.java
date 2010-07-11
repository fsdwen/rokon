package com.stickycoding.rokon;


/**
 * RenderQueueManager.java
 * This class manages the two RenderQueue buffers, should not be accessed from outside the engine.
 * The ideas behind this are taken from Replica Islands RenderSystem.java, along with (most of) the code.
 * 
 * Using this class imposes a fixed limit (256) on the total number of Drawables visible onscreen
 * 
 * @author Richard
 */

public class RenderQueueManager {
	
	private static final int RENDER_QUEUE_COUNT = 2;
	private static final int MAX_RENDER_OBJECTS_PER_QUEUE = ObjectManager.DEFAULT_ARRAY_SIZE;
	private static final int MAX_RENDER_OBJECTS = MAX_RENDER_OBJECTS_PER_QUEUE * RENDER_QUEUE_COUNT;
	
	private RenderElementPool elementPool;
	private ObjectManager[] renderQueue;
	private int queueIndex;
	
	protected RenderQueueManager() {
		elementPool = new RenderElementPool(MAX_RENDER_OBJECTS);
		renderQueue = new ObjectManager[RENDER_QUEUE_COUNT];
		for(int i = 0; i < RENDER_QUEUE_COUNT; i++) {
			renderQueue[i] = new ObjectManager(256);
		}
		queueIndex = 0;
	}
	
	protected void add(Drawable drawable, boolean useWindow) {
		RenderElement element = elementPool.allocate();
		if(element != null) {
			element.set(drawable, useWindow);
			renderQueue[queueIndex].add(element);
		}
	}
	
	private void clearQueue(FixedSizeArray<BaseObject> objects) {
		final int count = objects.getCount();
		final Object[] objectArray = objects.getArray();
		final RenderElementPool elementPool = this.elementPool;
        for (int i = count - 1; i >= 0; i--) {
        	RenderElement element = (RenderElement)objectArray[i];
        	elementPool.release(element);
    		objects.removeLast();
        }
	}
	
	protected void swap(RokonRenderer renderer) {
		renderQueue[queueIndex].commitUpdates();
		renderer.setDrawQueue(renderQueue[queueIndex]);
		final int lastQueue = queueIndex == 0 ? RENDER_QUEUE_COUNT - 1 : queueIndex - 1;
		FixedSizeArray<BaseObject> objects = renderQueue[lastQueue].getObjects();
		clearQueue(objects);
		queueIndex = (queueIndex + 1) % RENDER_QUEUE_COUNT;
	}
	
	public void emptyQueues(RokonRenderer renderer) {
		renderer.setDrawQueue(null);
		for(int i = 0; i < RENDER_QUEUE_COUNT; i++) {
			renderQueue[i].commitUpdates();
			FixedSizeArray<BaseObject> objects = renderQueue[i].getObjects();
			clearQueue(objects);
		}
	}
	
	public class RenderElement extends BaseObject {
		
        public RenderElement() {
            super();
        }

        public void set(Drawable drawable, boolean useWindow) {
        	this.drawable = drawable;
        	this.useWindow = useWindow;
        }

        public void reset() {
        	drawable = null;
        	useWindow = false;
        }

        public Drawable drawable;
        public boolean useWindow;
    }
	
	protected class RenderElementPool extends TObjectPool<RenderElement> {

        RenderElementPool(int max) {
            super(max);
        }

        @Override
        public void release(Object element) {
            RenderElement renderable = (RenderElement)element;
            renderable.reset();
            super.release(element);
        }

        @Override
        protected void fill() {
            for (int x = 0; x < getSize(); x++) {
                getAvailable().add(new RenderElement());
            }
        }
    }
	
	
	
}
