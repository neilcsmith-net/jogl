/*

Listens to NSViewGlobalFrameDidChangeNotification

This notification is sent whenever an NSView that has an attached NSSurface changes size or changes screens (thus potentially changing graphics hardware drivers.)

*/

#import <Cocoa/Cocoa.h>
#import <Foundation/Foundation.h>
#import <AppKit/NSView.h>
#import <OpenGL/OpenGL.h>
#import <OpenGL/gl.h>

//#define DEBUG_GL_LOCKS

#ifdef DEBUG_GL_LOCKS
    #define LOCK_GL(func, line) [ContextUpdater lockInFunction:func atLine:line];
    #define UNLOCK_GL(func, line) [ContextUpdater unlockInFunction:func atLine:line];
#else
    #define LOCK_GL(func, line) [ContextUpdater lock];
    #define UNLOCK_GL(func, line) [ContextUpdater unlock];
#endif

@interface ContextUpdater : NSObject
{
@protected
    NSView * view;
    NSRect viewRect;
    NSOpenGLContext *ctx;
    BOOL viewUpdated;
}

+ (void) lock;
+ (void) lockInFunction:(char *)func atLine:(int)line;
+ (void) unlock;
+ (void) unlockInFunction:(char *)func atLine:(int)line;

- (id) initWithContext:(NSOpenGLContext *)context view: (NSView *)nsView;

- (void) update:(NSNotification *)notification;

- (BOOL) needsUpdate;

@end
