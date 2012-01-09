/**
 * Copyright 2012 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 */
package jogamp.opengl.windows.wgl;

import java.security.AccessController;

import jogamp.nativewindow.windows.GDI;
import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;
import jogamp.opengl.Debug;

public class WGLUtil {
    /**
     * Switch to use the <code>wgl</code> variants of {@link jogamp.opengl.windows.wgl.WGL} 
     * to replace the following 5 GDI based functions (see below).
     * <p>
     * Disabled per default.
     * </p> 
     * <p>
     * You can enable it by defining the property <code>jogl.windows.useWGLVersionOf5WGLGDIFuncSet</code>.
     * </p>
     * 
     * @see jogamp.nativewindow.windows.GDI#ChoosePixelFormat(long, PIXELFORMATDESCRIPTOR)
     * @see jogamp.nativewindow.windows.GDI#DescribePixelFormat(long, int, int, PIXELFORMATDESCRIPTOR)
     * @see jogamp.nativewindow.windows.GDI#GetPixelFormat(long)
     * @see jogamp.nativewindow.windows.GDI#SetPixelFormat(long, int, PIXELFORMATDESCRIPTOR)
     * @see jogamp.nativewindow.windows.GDI#SwapBuffers(long)
     */
    public static final boolean USE_WGLVersion_Of_5WGLGDIFuncSet;
    
    static {
        USE_WGLVersion_Of_5WGLGDIFuncSet = Debug.isPropertyDefined("jogl.windows.useWGLVersionOf5WGLGDIFuncSet", true, AccessController.getContext());
        System.err.println("USE_WGLVersion_Of_5WGLGDIFuncSet: "+USE_WGLVersion_Of_5WGLGDIFuncSet);
    }

    public static int ChoosePixelFormat(long hdc, PIXELFORMATDESCRIPTOR pfd)  {
        if(USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglChoosePixelFormat(hdc, pfd);
        } else {
            return GDI.ChoosePixelFormat(hdc, pfd);
        }        
    }
    public static int DescribePixelFormat(long hdc, int pfdid, int pfdSize, PIXELFORMATDESCRIPTOR pfd)  {
        if(USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglDescribePixelFormat(hdc, pfdid, pfdSize, pfd);
        } else {
            return GDI.DescribePixelFormat(hdc, pfdid, pfdSize, pfd);
        }                
    }
    public static int GetPixelFormat(long hdc)  {
        if(USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglGetPixelFormat(hdc);
        } else {
            return GDI.GetPixelFormat(hdc);
        }                
    }
    public static boolean SetPixelFormat(long hdc, int pfdid, PIXELFORMATDESCRIPTOR pfd)  {        
        if(USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglSetPixelFormat(hdc, pfdid, pfd);
        } else {
            return GDI.SetPixelFormat(hdc, pfdid, pfd);
        }                
    }
    public static boolean SwapBuffers(long hdc)  {        
        if(USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglSwapBuffers(hdc);
        } else {
            return GDI.SwapBuffers(hdc);
        }                
    }
}
