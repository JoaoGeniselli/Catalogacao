package br.com.jgeniselli.catalogacaolem.pendenciesSync;

import br.com.jgeniselli.catalogacaolem.common.service.NestSyncController;

/**
 * Created by jgeniselli on 17/09/17.
 */

public abstract class PendenciesState {

    public abstract void startSynchronizing(NestSyncController syncController, PendenciesActivity context);

    public abstract PendenciesState nextState();

    public abstract void onSynchronizationFinish(PendenciesActivity context);

    public static class PendenciesStateSynchronizing extends PendenciesState {

        @Override
        public void startSynchronizing(NestSyncController syncController, PendenciesActivity context) {

        }

        @Override
        public PendenciesState nextState() {
            return new PendenciesStateDefault();
        }

        @Override
        public void onSynchronizationFinish(PendenciesActivity context) {
            context.setState(nextState());
        }
    }

    public static class PendenciesStateDefault extends PendenciesState {

        @Override
        public void startSynchronizing(NestSyncController syncController, PendenciesActivity context) {
            context.startLoading();
            context.setState(nextState());
        }

        @Override
        public PendenciesState nextState() {
            return new PendenciesStateSynchronizing();
        }

        @Override
        public void onSynchronizationFinish(PendenciesActivity context) {

        }
    }
}
