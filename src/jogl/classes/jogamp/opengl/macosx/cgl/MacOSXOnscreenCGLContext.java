/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 * 
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

package jogamp.opengl.macosx.cgl;


import javax.media.opengl.*;

public class MacOSXOnscreenCGLContext extends MacOSXCGLContext {

  public MacOSXOnscreenCGLContext(MacOSXOnscreenCGLDrawable drawable,
                                 GLContext shareWith) {
    super(drawable, shareWith);
  }

  @Override
  protected void makeCurrentImpl(boolean newCreated) throws GLException {
      super.makeCurrentImpl(newCreated);
      drawableUpdatedNotify();  
  }
    
  @Override
  protected void releaseImpl() throws GLException {
    super.releaseImpl();
  }

  @Override
  protected void swapBuffers() {
    if (!CGL.flushBuffer(contextHandle)) {
      throw new GLException("Error swapping buffers");
    }
  }

  @Override
  protected void drawableUpdatedNotify() throws GLException {
    if(0==updateHandle || CGL.updateContextNeedsUpdate(updateHandle)) {
        if (contextHandle == 0) {
          throw new GLException("Context not created");
        }
        CGL.updateContext(contextHandle);
    }
  }
  
  protected long updateHandle = 0;
  
  @Override
  protected boolean createImpl() {
    boolean res = create(false, false);
    if(res && isNSContext) {
        if(0 != updateHandle) {
            throw new InternalError("XXX1");
        }
        updateHandle = CGL.updateContextRegister(contextHandle, drawable.getHandle());
        if(0 == updateHandle) {
            throw new InternalError("XXX2");
        }
    }
    return res;
  }

  @Override
  protected void destroyImpl() throws GLException {
    if ( 0 != updateHandle ) {
        CGL.updateContextUnregister(updateHandle);
        updateHandle = 0;
    }
    super.destroyImpl();
  }
  
  @Override
  public void setOpenGLMode(int mode) {
    if (mode != MacOSXCGLDrawable.NSOPENGL_MODE)
      throw new GLException("OpenGL mode switching not supported for on-screen GLContexts");
  }
    
  @Override
  public int  getOpenGLMode() {
    return MacOSXCGLDrawable.NSOPENGL_MODE;
  }
}
